package com.o7planning.entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Authority khac Role o cho khi vao CSDL thi Role phai co tien to ROLE_User vai tro, con auth thi ten duoc mo ta nhu ten quyen han
@Entity
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="role_id")
    private Long id;


    @Column(name = "role_name")
    private String name;
}
