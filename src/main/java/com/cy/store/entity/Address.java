package com.cy.store.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;
@Data
@AllArgsConstructor
public class Address extends BaseEntity{
    private Integer aid;
    private Integer uid;
    private String name;
    private String provinceName;
    private String provinceCode;
    private String cityName;
    private String cityCode;
    private String areaName;
    private String areaCode;
    private String zip;
    private String address;
    private String phone;
    private String tel;
    private String tag;
    private Integer isDefault;

    public Address() {

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        if (!super.equals(o)) return false;
        Address address1 = (Address) o;
        return Objects.equals(getAid(), address1.getAid()) && Objects.equals(getUid(), address1.getUid()) && Objects.equals(getName(), address1.getName()) && Objects.equals(getProvinceName(), address1.getProvinceName()) && Objects.equals(getProvinceCode(), address1.getProvinceCode()) && Objects.equals(getCityName(), address1.getCityName()) && Objects.equals(getCityCode(), address1.getCityCode()) && Objects.equals(getAreaName(), address1.getAreaName()) && Objects.equals(getAreaCode(), address1.getAreaCode()) && Objects.equals(getZip(), address1.getZip()) && Objects.equals(getAddress(), address1.getAddress()) && Objects.equals(getPhone(), address1.getPhone()) && Objects.equals(getTel(), address1.getTel()) && Objects.equals(getTag(), address1.getTag()) && Objects.equals(getIsDefault(), address1.getIsDefault());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getAid(), getUid(), getName(), getProvinceName(), getProvinceCode(), getCityName(), getCityCode(), getAreaName(), getAreaCode(), getZip(), getAddress(), getPhone(), getTel(), getTag(), getIsDefault());
    }

    @Override
    public String toString() {
        return "Address{" +
                "aid=" + aid +
                ", uid=" + uid +
                ", name='" + name + '\'' +
                ", provinceName='" + provinceName + '\'' +
                ", provinceCode='" + provinceCode + '\'' +
                ", cityName='" + cityName + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", areaName='" + areaName + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", zip='" + zip + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", tel='" + tel + '\'' +
                ", tag='" + tag + '\'' +
                ", isDefault=" + isDefault +
                '}';
    }

}
