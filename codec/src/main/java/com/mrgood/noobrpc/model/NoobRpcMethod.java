package com.mrgood.noobrpc.model;

import java.util.Arrays;
import java.util.Objects;

public class NoobRpcMethod {
    private final String methodName;
    private final String[] argsClasses;
    private final String retClass;
    private final String interfaceName;

    public String getMethodName() {
        return methodName;
    }

    public String[] getArgsClasses() {
        return argsClasses;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public String getRetClass() {
        return retClass;
    }

    public NoobRpcMethod(String methodName, String[] argsClasses, String retClass, String interfaceName) {
        this.methodName = methodName;
        this.argsClasses = argsClasses;
        this.retClass = retClass;
        this.interfaceName = interfaceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoobRpcMethod that = (NoobRpcMethod) o;
        return methodName.equals(that.methodName) &&
                Arrays.equals(argsClasses, that.argsClasses) &&
                Objects.equals(retClass, that.retClass) &&
                interfaceName.equals(that.interfaceName);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(methodName, retClass, interfaceName);
        result = 31 * result + Arrays.hashCode(argsClasses);
        return result;
    }

    @Override
    public String toString() {
        return "NoobRpcMethod{" +
                "methodName='" + methodName + '\'' +
                ", argsClasses=" + Arrays.toString(argsClasses) +
                ", retClass='" + retClass + '\'' +
                ", interfaceName='" + interfaceName + '\'' +
                '}';
    }
}
