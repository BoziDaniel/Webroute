package webroute;

public class Routes {
    @WebRoute("/test")
    public String test1() {
        return "zsiráf ";
    }

    @WebRoute("/another")
    public String test2() {
        return " keszeg ";
    }
}
