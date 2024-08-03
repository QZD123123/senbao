package com.senbao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName supplier
 */
@TableName(value ="supplier")
@Data
public class Supplier implements Serializable {
    private Integer id;

    private String name;

    private String phone;

    private String company;

    private static final long serialVersionUID = 1L;
}