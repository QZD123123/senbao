package com.senbao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName consumption
 */
@TableName(value ="consumption")
@Data
public class Consumption implements Serializable {
    private Integer id;

    private Integer orderId;

    private Integer warehouseId;

    private Integer materialId;

    private Integer quantity;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}