package net.ukr.dreamsicle.exception;

/**
 * JMSException class that extends {@link JMSException}
 *
 * @author yurii.loienko
 * @version 1.0
 */
public class JMSException extends javax.jms.JMSException {
    public JMSException(String message) {
        super(message);
    }
}
