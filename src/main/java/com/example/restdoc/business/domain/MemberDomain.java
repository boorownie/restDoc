package com.example.restdoc.business.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by phpbae on 2017-07-11.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberDomain {

    String name;
    int age;

}
