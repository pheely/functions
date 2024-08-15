package com.bns.hstcld.functions.cloud_event;

import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PubSubBody {
  private Message message;
  private String subscription;

  @Data
  @NoArgsConstructor
  public class Message {
    private String data;
    private String messageId;
    private String message_id;
    private Date publishTime;
    private Date publish_time;
  }
}

