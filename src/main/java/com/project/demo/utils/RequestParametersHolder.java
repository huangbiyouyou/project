package com.project.demo.utils;


public class RequestParametersHolder {

    private AlipayHashMap protocalMustParams;
    private AlipayHashMap protocalOptParams;
    private AlipayHashMap applicationParams;

    public RequestParametersHolder() {
    }

    public AlipayHashMap getProtocalMustParams() {
        return this.protocalMustParams;
    }

    public void setProtocalMustParams(AlipayHashMap protocalMustParams) {
        this.protocalMustParams = protocalMustParams;
    }

    public AlipayHashMap getProtocalOptParams() {
        return this.protocalOptParams;
    }

    public void setProtocalOptParams(AlipayHashMap protocalOptParams) {
        this.protocalOptParams = protocalOptParams;
    }

    public AlipayHashMap getApplicationParams() {
        return this.applicationParams;
    }

    public void setApplicationParams(AlipayHashMap applicationParams) {
        this.applicationParams = applicationParams;
    }
}
