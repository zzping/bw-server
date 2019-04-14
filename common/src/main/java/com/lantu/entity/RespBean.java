package com.lantu.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author Trig
 * @create 2019-04-13 0:08
 */
@Data
@Builder
public class RespBean {

    private String status;
    private String msg;
}
