package de.java2html.plugin.jspwiki;

import java.net.URL;

import com.ecyrd.jspwiki.WikiContext;
import com.ecyrd.jspwiki.attachment.Attachment;
import com.ecyrd.jspwiki.attachment.AttachmentManager;
import com.ecyrd.jspwiki.plugin.PluginException;
import com.ecyrd.jspwiki.providers.ProviderException;

/**
 * @author Markus Gebhard
 */
public class PluginSecurityManager {
  private static final String FILE_URL_PROPERTY = "de.java2html.file.url.enabled";
  private static final String HTTP_URL_PROPERTY = "de.java2html.http.url.enabled";

  private WikiContext context;

  public PluginSecurityManager(WikiContext context) {
    this.context = context;
  }

  public void checkUrlAccessEnabled(URL url) throws PluginException {
    if ("file".equals(url.getProtocol())) {
      if (!isPropertySetTrue(context, FILE_URL_PROPERTY)) {
        throw new PluginException(
          "File URLs are disabled in this Wiki (property '" + FILE_URL_PROPERTY + "' is not set to true).");
      }
    }
    else if ("http".equals(url.getProtocol())) {
      if (!isPropertySetTrue(context, HTTP_URL_PROPERTY)) {
        throw new PluginException(
          "Http URLs are disabled in this Wiki (property '" + HTTP_URL_PROPERTY + "' is not set to true).");
      }
    }
    else {
      throw new PluginException("Unsupported protocol: '" + url.getProtocol() + "'");
    }
  }

  private boolean isPropertySetTrue(WikiContext context, String key) {
    Object value = context.getEngine().getWikiProperties().get(key);
    return value != null && "true".equals(value);
  }

  public void checkValidAttachmentUrlPart(String attachment) throws PluginException {
    AttachmentManager attachmentManager = context.getEngine().getAttachmentManager();
    if (!attachmentManager.attachmentsEnabled()) {
      throw new PluginException("Attachments are not enabled in this Wiki.");
    }
    if (!attachmentManager.hasAttachments(context.getPage())) {
      throw new PluginException("The current page does not have any attachments.");
    }
    Attachment attachmentInfo = null;
    try {
      attachmentInfo = attachmentManager.getAttachmentInfo(context, attachment);
    }
    catch (ProviderException e) {
      throw new PluginException("The current page does not have an attachment '" + attachment + "'");
    }
    if (attachmentInfo == null) {
      throw new PluginException("The current page does not have an attachment '" + attachment + "'");
    }
  }
}