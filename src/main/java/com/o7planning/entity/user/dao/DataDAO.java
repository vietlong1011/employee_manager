package com.o7planning.entity.user.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Transactional
public class DataDAO extends JdbcDaoSupport {

    @Autowired
    public DataDAO(DataSource dataSource){
        this.setDataSource(dataSource);
    }

    public List<String> findRolesByUsername(String username){
        String sql ="SELECT r.role_name " +
                "FROM jwt_spring_security.users u " +
                "JOIN jwt_spring_security.user_role ur ON u.user_id = ur.user_id " +
                "JOIN jwt_spring_security.roles r ON r.role_id = ur.role_id " +
                "WHERE u.username = ?";
        List<String> roles = this.getJdbcTemplate().queryForList(sql, new Object[]{username}, String.class);
        return roles;
    }

}
