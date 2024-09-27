package ro.ps.showmgmtbackend.jms;

import ro.ps.showmgmtbackend.dto.mail.SendingStatus;

public interface MessageSender<Request> {

    SendingStatus sendMessage(Request request);
}
