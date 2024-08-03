package com.senbao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName warehouse
 */
@TableName(value ="warehouse")
@Data
public class Warehouse implements Serializable {
    private Integer id;

    private String location;

    private Integer managerid;

    private static final long serialVersionUID = 1L;
}