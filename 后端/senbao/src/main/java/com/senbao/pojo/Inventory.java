package com.senbao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName inventory
 */
@TableName(value ="inventory")
@Data
public class Inventory implements Serializable {
    private Integer id;

    private Integer materialId;

    private Integer warehouseId;

    private Integer quantity;

    private static final long serialVersionUID = 1L;
}