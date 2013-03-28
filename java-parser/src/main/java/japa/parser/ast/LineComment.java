/*
 * Created on 23/05/2008
 */
package japa.parser.ast;

import japa.parser.ast.visitor.GenericVisitor;
import japa.parser.ast.visitor.VoidVisitor;

/**
 * @author Julio Vilmar Gesser
 */
public class LineComment extends Comment {

    public LineComment(int beginLine, int beginColumn, int endLine, int endColumn, String content) {
        super(beginLine, beginColumn, endLine, endColumn, content);
    }

    @Override
    public <A> void accept(VoidVisitor<A> v, A arg) {
        v.visit(this, arg);
    }

    @Override
    public <R, A> R accept(GenericVisitor<R, A> v, A arg) {
        return v.visit(this, arg);
    }
}
