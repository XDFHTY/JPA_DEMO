package com.girl.ppl;

public class TestClass {

    public static void main(String[] args) {
        String[] strings = {
                "《呋喃树脂防腐蚀工程技术规范》",
                " 【呋喃树脂】防腐蚀工程技术规程(附条文说明)"
        };

        new HanLPDemo().HanLPPPL(strings);
        System.out.println();
        new IKUtil().zh_cnPPL(strings);
        System.out.println();
        new JieBaDemo().JieBaPPL(strings);
    }
}
