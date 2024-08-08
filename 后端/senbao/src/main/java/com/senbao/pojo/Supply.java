package com.senbao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @TableName supply
 */
@TableName(value ="supply")
@Data
public class Supply implements Serializable {
    private Integer id;

    private Integer supplierId;

    private Integer materialId;

    private Integer quantity;

    private Integer warehouseId;

    private BigDecimal cost;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}