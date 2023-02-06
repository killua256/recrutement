package com.recrutement.utils.email;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailData {
    private String from;
    private String to;
    private String subject;
    private String filePath;
    private String  passowrd;
    private String host;
    private Integer port;
    private String protocol;
}
