public class Test123 {
    public static void main(String[] args) {
        A a1 = new B();
        a1.m(a1);
        C c1 = new C();
        c1.m(a1);
    }
    static class A{
        public void m(A c){
            System.out.println("AA");
        }
        public void m(B c){
            System.out.println("AB");
        }
    }
    static class B extends A{
        public void m(A c){
            System.out.println("BA");
        }
        public void m(B c){
            System.out.println("BB");
        }
    }
    static class C extends B{
        public void m(A c){
            System.out.println("CA");
        }
        public void m(B c){
            System.out.println("CB");
        }
    }
}

