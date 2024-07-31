package com.senbao.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Order implements Serializable {
    @TableId
    private Integer id;
    private String address;
    private String totalPrice;
    private String customerName;
    private String phone;
    private Integer progress;
    private Date createTime;
}
