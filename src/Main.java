public class Main {
    public static void main(String[] args) {
        Window w = new Window();
        w.display();
        Data data = new Data();
        System.out.println(data.getAllData().get("2023S").get("SP101").keySet());
    }
}