package com.senbao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName material
 */
@TableName(value ="material")
@Data
public class Material implements Serializable {
    private Integer id;

    private String name;

    private Object type;

    private String description;

    private static final long serialVersionUID = 1L;
}