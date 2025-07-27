package com.smc.netty.protocoltcp;

/**
 * @Date 2022/12/4
 * @Author smc
 * @Description:协议包
 */

public class MessageProtocol {
    private Integer len;
    private byte[] content;

    public Integer getLen() {
        return len;
    }

    public void setLen(Integer len) {
        this.len = len;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
