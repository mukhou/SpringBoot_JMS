/* 
User: Urmi
Date: 3/5/2020 
Time: 2:36 PM
*/

package com.springframework.guru.sfgjms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HelloWorldMessage implements Serializable {

    static final long serialVersionUID = 42L;

    private UUID id;
    private String message;
}
