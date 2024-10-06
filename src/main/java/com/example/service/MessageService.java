package com.example.service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;



@Service
public class MessageService {
    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    // GET ALL MESSAGES
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    // GET MESSAGE BY ID
    public Message getMessageById(int messageId) {
        // find message first
        Optional<Message> optionalMessage = messageRepository.findById(messageId);

        // check if message exists, then get
        if (optionalMessage.isPresent()) {
            return optionalMessage.get();
        } else {
            throw new IllegalArgumentException("Invalid messageId");
        }
    }

    // GET MESSAGES OF USER
    public List<Message> getMessagesOfUser(int postedBy) {
        return messageRepository.findByPostedBy(postedBy);
    }

    // VALIDATE MESSAGE
    private void validateMessage(Message message) {
        // check if messageText is null, but also if empty string.
        if (message.getMessageText() == null || message.getMessageText().trim().isEmpty()) {
            throw new IllegalArgumentException("Message Text cannot be empty");
        }

        // check if messageText > 255 limit
        if (message.getMessageText().length() > 255) {
            throw new IllegalArgumentException("Message Text cannot be more than 255 characters long");
        }

        // check if postedBy is nonexistent (use method from AccountRepo)
        if (accountRepository.existsByAccountId(message.getPostedBy())) {
            throw new IllegalArgumentException("This user does not exist");
        }
    }

    // CREATE MESSAGE
    public Message addMessage(Message newMessage) {
        // validate fields first
        validateMessage(newMessage);

        // then create
        return messageRepository.save(newMessage);
    }

    // UPDATE MESSAGE
    public Message updateMessage(int messageId, Message updatedMessage) {
        // validate updatedMessage
        validateMessage(updatedMessage);

        // get possible message (this is Optional type)
        Optional<Message> optionalMessage = messageRepository.findById(messageId);

        // if it exists update
        if (optionalMessage.isPresent()) {
            //convert Optional type to Message object
            Message chosenMessage = optionalMessage.get();

            chosenMessage.setMessageText(updatedMessage.getMessageText());          // updatable
            chosenMessage.setTimePostedEpoch(updatedMessage.getTimePostedEpoch());  // updatable

            return messageRepository.save(chosenMessage);       // updates and returns updated original message
        } else {
            throw new IllegalArgumentException("Invalid messageId");
        }
    }

    // DELETE MESSAGE
    public Integer deleteMessage(int messageId) {       // Integer includes null
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return 1;       // successful deletion return
        } else {
            throw new IllegalArgumentException("Invalid messageId");   // return is already null
        }
    }

}
