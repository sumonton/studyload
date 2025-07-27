package com.smc.crawler.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Date 2022/5/23
 * @Author smc
 * @Description:
 */
@Data
@TableName(value = "car_test")
public class CarTest {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private int testSpeed;
    private int testBrake;
    private int testOil;
    private String editorName1;
    private String editorRemark1;
    private String editorName2;
    private String editorRemark2;
    private String editorName3;
    private String editorRemark3;
    private String image;
    private Date created;
    private Date updated;
}
