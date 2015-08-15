package com.epam.tour.notification.tag;

import com.epam.tour.notification.entity.Notification;
import com.epam.tour.notification.service.NotificationService;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.util.Iterator;

/**
 * The type Flashes tag.
 */
public class FlashesTag extends BodyTagSupport {
    /**
     * The constant MESSAGE.
     */
    private static final String MESSAGE = "{message}";
    /**
     * The constant TYPE.
     */
    private static final String TYPE = "{type}";
    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = 4588886236966633563L;
    /**
     * The Body content.
     */
    private BodyContent bodyContent = null;
    /**
     * The Pattern.
     */
    private String pattern = null;
    /**
     * The Iterator.
     */
    private Iterator<Notification> iterator = null;


    /**
     * Do start tag.
     *
     * @return the int
     * @throws JspException the jsp exception
     */
    @Override
    public int doStartTag() throws JspException {
        iterator = NotificationService.getNotifications(pageContext.getSession()).iterator();

        return EVAL_BODY_BUFFERED;
    }

    /**
     * Do after body.
     *
     * @return the int
     * @throws JspException the jsp exception
     */
    @Override
    public int doAfterBody() throws JspException {
        BodyContent bodyContent = this.bodyContent;

        if (pattern == null) {
            String rawPattern = this.bodyContent.getString();
            if (rawPattern == null || rawPattern.trim().isEmpty()) {
                return SKIP_BODY;
            }

            pattern = rawPattern;
        }

        //While have notifications
        if (iterator != null && iterator.hasNext()) {

            Notification notification = iterator.next();
            iterator.remove();
            if (notification != null) {
                String message = formatMessage(notification);

                if (message != null) {
                    try {
                        bodyContent.getEnclosingWriter().write(message);
                    } catch (IOException e) {
                       throw  new IllegalArgumentException(e);
                    }
                }
            }
            return EVAL_BODY_AGAIN;
        }

        return SKIP_BODY;
    }

    /**
     * Format message.
     *
     * @param notification the notification
     * @return the string
     */
    private String formatMessage(Notification notification) {

        StringBuilder content = new StringBuilder(pattern);
        String message = notification.getMessage();

        if (message != null && content.length() > 0) {

            // Replace {type} with notification type
            int i = content.indexOf(TYPE);
            if (i > 0) {
                content.replace(i, i + TYPE.length(), notification.getType().toString().toLowerCase());
            }

            // Replace {getMessage} with actual getMessage
            int of = content.indexOf(MESSAGE);
            if (of > 0) {
                content.replace(of, of + MESSAGE.length(), message);
            }

            return content.toString();
        }

        return null;
    }

    /**
     * Gets body content.
     *
     * @return the body content
     */
    @Override
    public BodyContent getBodyContent() {
        return bodyContent;
    }

    /**
     * Sets body content.
     *
     * @param b the b
     */
    @Override
    public void setBodyContent(BodyContent b) {
        bodyContent = b;
    }
}
