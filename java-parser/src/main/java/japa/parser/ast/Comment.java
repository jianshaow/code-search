/*
 * Created on 23/05/2008
 */
package japa.parser.ast;

/**
 * @author Julio Vilmar Gesser
 */
public abstract class Comment extends Node {

    private final String content;

    public Comment(int beginLine, int beginColumn, int endLine, int endColumn, String content) {
        super(beginLine, beginColumn, endLine, endColumn);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

}
