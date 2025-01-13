package Test;

interface interf1 {
}
public class Exercice1 {
    static class Inner1 {
    }
    static interface innerInterf1 {
    }
    private class Inner2 {
        class InnerInner1 implements innerInterf1, interf1 {
        }
        class InnerInner2 extends InnerInner1 {
        }
    }
    public static String represente(Class<?> cls){
        String result = "";
        if(cls.isInterface()){
            result = result+"interface ";
        }else{
            result = result+"class ";
        }
        result += cls.getSimpleName() ;
        Class<?>[] interfaces = cls.getInterfaces();
        for(int i = 0;i<interfaces.length; i++){
            if(i==0 ) result += " implements "+interfaces[i].getSimpleName();
            else{
                result+= ", "+interfaces[i].getSimpleName();
            }
        }
        Class<?> superClass = cls.getSuperclass();
        if(superClass != null  && superClass != Object.class){
            result += " extends "+superClass.getSimpleName();
        }
        result += " { \n";
        Class<?>[] classes = cls.getDeclaredClasses();
        for(Class<?> cl : classes){
            result += represente(cl) + " \n } \n";
        }
        return result;
    }
    public static void classRepresentation(Class<?> cls) {
       String result= "package "+cls.getPackage().getName()+";\n\n";
       result += represente(cls)+ "\n}\n";
       System.out.println(result);
    }
    public static void main(String[] args) {
        classRepresentation(Exercice1.class);
        classRepresentation(interf1.class);
    }
}
