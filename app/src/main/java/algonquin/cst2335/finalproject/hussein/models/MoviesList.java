package algonquin.cst2335.finalproject.hussein.models;

public class MoviesList {

    String title;
    String[] list;

    public MoviesList(String title, String[] list) {
        this.title = title;
        this.list = list;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getList() {
        return list;
    }

    public void setList(String[] list) {
        this.list = list;
    }
}
