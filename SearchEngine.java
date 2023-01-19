import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    int num = 0;
    ArrayList<String> searches = new ArrayList<>();
    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("This is Max's Search Engine");
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if(parameters[0].equals("s")){
                    searches.add(parameters[1]);
                    return String.format("\"%s\" Added", parameters[1]);
                }
            }
            if (url.getPath().contains("/search")){
                String search = url.getQuery().split("=")[1];
                String result = "";
                for(int i = 0; i < searches.size(); i++){
                    if(searches.get(i).contains(search)){
                        result += searches.get(i)+"\n";
                    }
                }
                if (result.length() == 0){
                    return "No search results found";
                }
                return result; 
            }
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
