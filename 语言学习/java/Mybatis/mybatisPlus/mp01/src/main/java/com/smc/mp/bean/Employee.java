package com.smc.mp.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * @Date 2022/4/10
 * @Author smc
 * @Description:
 */
//@TableName(value = "tb1_employee")
public class Employee extends Model {
    /**
     * value:指定表中主键列的列名，若属性名与列名相同则不用设置
     * type：设置主键策略
     */
//    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String lastName;
    private String gender;
    @TableField(fill = FieldFill.INSERT)
    private String email;
    private Integer age;
    @TableField(exist = false)
    private Double salary;
    @Version
    private Integer version;

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                ", version=" + version +
                ", isDelete=" + isDelete +
                '}';
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @TableLogic
    private Integer isDelete;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
