package com.hzw.signcalendarprogect;

public class DingDan {

    private String bianhao,xingming,dianhua,dizhi;
    private int tupian;

    public DingDan(){
        super();
    }

    public void setTupian(int tp){
        tupian = tp;
    }
    public int getTupian(){
        return tupian;
    }

    public void setBianhao(String bh){
        bianhao = bh;
    }
    public String getBianhao(){
        return bianhao;
    }

    public void setXingming(String xm){
        xingming = xm;
    }
    public String getXingming(){
        return xingming;
    }

    public void setDianhua(String dh){
        dianhua = dh;
    }
    public String getDianhua(){
        return dianhua;
    }

    public void setDizhi(String dz){
        dizhi = dz;
    }
    public String getDizhi(){
        return dizhi;
    }

}