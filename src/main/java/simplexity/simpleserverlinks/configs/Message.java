package simplexity.simpleserverlinks.configs;

public enum Message {
    CONFIG_RELOADED("feedback.config-reloaded", "<yellow>Server Links config has been reloaded</yellow>"),
    LINK_HEADER("feedback.link.header", "Check out some more stuff about our server: "),
    LINK_LAYOUT("feedback.link.layout", "\n[<title>]\n- <click:open_url:'<link>'><link></click>\n- <desc>"),
    NO_LINKS("feedback.link.no-links", "There's no links available on this server");
    private final String path;
    private String message;

    Message(String path, String message) {
        this.path = path;
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
