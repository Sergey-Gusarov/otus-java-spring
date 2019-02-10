package ru.sergey_gusarov.hw27.domain.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "lib_user")
public class LibUser {
    @Id
    @Indexed
    private String id;

    private String userName;

    private String password;

}
