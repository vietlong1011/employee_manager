package com.o7planning.service.oauth2;



import com.o7planning.entity.user.User;
import com.o7planning.exception.BaseException;
import com.o7planning.repository.URepository.RoleRepository;
import com.o7planning.repository.URepository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Mục đích chính của lớp DefaultOAuth2UserService là cung cấp một cách để truy xuất thông tin người dùng từ
 * OAuth2 Provider và chuyển đổi nó thành một đối tượng OAuth2User (người dùng OAuth2)
 * Nó triển khai giao diện OAuth2UserService và được sử dụng để
 * xác thực và tải thông tin người dùng từ OAuth2 Provider (ví dụ: Google, Facebook, GitHub, etc.).
 **/


@Service
@RequiredArgsConstructor
public class CustomOAuth2UserDetailService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    // method run when need load information from OAuth2 Provider
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest); // lay toan bo thuoc tinh cua userRequest (username , role , gmail,...)

        try {
            return checkingOAuth2User(userRequest, oAuth2User);
        } catch (
                AuthenticationException e) { // ngoại lệ cụ thể liên quan đến xác thực người dùng trong quá trình xác thực và ủy quyền.(sai tk ,mk)
            throw e;
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());// ngoai le xac thuc noi bo(loi xac minh ,xac thuc danh tinh , uy quyen) .dau vao : mo ta loi , nguyen nhan
        }
    }

    // ham check tinh xac thuc cua request gui den
    private OAuth2User checkingOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) { // yeu cau xac thuc duoc gui den
        OAuth2UserDetail oAuth2UserDetails = OAuth2UserDetailFactory
                .getOAuth2Detail(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes()); //oAuth2UserDetails duoc tao ra de  chua ID dang ky cua khach hang trong phien giao dich va cac thuoc tinh quan trong khac (email , role , username,..)
        if (ObjectUtils.isEmpty(oAuth2UserDetails)) { // neu khong ton tai -> ngoai le
            throw new BaseException("400", "Can not found OAuth2 user from properties");
        }

        Optional<User> user = userRepository.findUsernameAndProviderID( // lay user dua theo username va ID cua trinh duyet duoc su dung
                oAuth2UserDetails.getEmail(),
                oAuth2UserRequest.getClientRegistration().getRegistrationId());
        User userDetail;
        if (user.isPresent()) { // neu nguoi dung da ton tai ->
            userDetail = user.get();
            // kiem tra xem user da dang nhap bang cung dich vu OAuth2 ma yeu cau xac thuc dang su dung k?
            if (!userDetail.getProviderID().equals((oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
                throw new BaseException("400", "Invalid site login with " + userDetail.getProviderID());
            }
            userDetail = updateOAuth2Detail(userDetail, oAuth2UserDetails);
            // cap nhap thong tin xac thuc moi nhat cho nguoi dung(thay doi username thanh email login)
        } else { // neu chua ton tai -> them dang nhap moi
            userDetail = registerNewOAuth2UserDetail(oAuth2UserRequest, oAuth2UserDetails);

        }

        return new OAuth2UserDetailCustom(
                userDetail.getId(),
                userDetail.getUsername(),
                userDetail.getPassword(),
                userDetail.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList()));

    }

    // method nay se them thong tin cua user vao DB moi khi co user moi su dung OAuth2
    public User registerNewOAuth2UserDetail(OAuth2UserRequest oAuth2UserRequest, OAuth2UserDetail oAuth2UserDetail) {
        User user = new User();
        user.setUsername(oAuth2UserDetail.getEmail());

        user.setProviderID(oAuth2UserRequest.getClientRegistration().getRegistrationId());
        user.setEnabled(true);
        user.setCredentialsNonExpired(true);
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setRoles(new HashSet<>());
        user.getRoles().add(roleRepository.findByName("USER"));

        return userRepository.save(user);
    }

    // method nay se lay email cua user khi dung OAuth2 de lam username
    public User updateOAuth2Detail(User user, OAuth2UserDetail oAuth2UserDetail) {
        user.setUsername(oAuth2UserDetail.getEmail());
        return userRepository.save(user);
    }
}
