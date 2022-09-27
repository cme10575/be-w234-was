package model;

public enum ContentType {
    HTML("text/html;charset=utf-8"),
    CSS("text/css;charset=utf-8");

    private String headerValue;

    ContentType(String headerValue) {
        this.headerValue = headerValue;
    }

    public String getHeaderValue() {
        return headerValue;
    }
}
