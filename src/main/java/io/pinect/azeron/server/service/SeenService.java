package io.pinect.azeron.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.pinect.azeron.server.domain.dto.SeenDto;
import io.pinect.azeron.server.domain.dto.SeenResponseDto;
import io.pinect.azeron.server.domain.entity.MessageEntity;
import io.pinect.azeron.server.domain.model.ResponseStatus;
import io.pinect.azeron.server.domain.repository.MessageRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class SeenService {
    private final MessageRepository messageRepository;
    private final ObjectMapper objectMapper;

    public SeenService(MessageRepository messageRepository, ObjectMapper objectMapper) {
        this.messageRepository = messageRepository;
        this.objectMapper = objectMapper;
    }

    public SeenResponseDto seen(SeenDto seenDto){
        if(seenDto.getMessageId() != null) {
            MessageEntity messageEntity = messageRepository.seenMessage(seenDto.getMessageId(), seenDto.getServiceName());
            removeMessageIdNeeded(messageEntity);
        } else
            messageRepository.seenMessages(seenDto.getMessageIds(), seenDto.getServiceName());

        return new SeenResponseDto(ResponseStatus.OK, seenDto.getReqId());
    }

    private void removeMessageIdNeeded(MessageEntity messageEntity) {
        if(messageEntity.getSeenNeeded() == messageEntity.getSeenCount() || messageEntity.getSubscribers().size() == messageEntity.getSeenSubscribers().size())
            messageRepository.removeMessage(messageEntity.getMessageId());
    }

}
