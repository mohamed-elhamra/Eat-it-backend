package com.melhamra.eatItBackend.services;

import com.melhamra.eatItBackend.dtos.UserDto;
import com.melhamra.eatItBackend.entities.OrderEntity;
import com.melhamra.eatItBackend.entities.OrderProductEntity;
import com.melhamra.eatItBackend.entities.UserEntity;
import com.melhamra.eatItBackend.exceptions.EatItException;
import com.melhamra.eatItBackend.repositories.OrderProductRepository;
import com.melhamra.eatItBackend.repositories.OrderRepository;
import com.melhamra.eatItBackend.repositories.UserRepository;
import com.melhamra.eatItBackend.responses.OrderProductResponse;
import com.melhamra.eatItBackend.responses.OrderResponse;
import com.melhamra.eatItBackend.utils.IDGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    OrderProductRepository orderProductRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IDGenerator idGenerator;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        Optional<UserEntity> userByEmail = userRepository.findByEmail(userDto.getEmail());
        userByEmail.ifPresent(user -> {
            throw new EatItException("User already exits with this email !");
        });

        Optional<UserEntity> userByPhone = userRepository.findByPhone(userDto.getPhone());
        userByPhone.ifPresent(user -> {
            throw new EatItException("User already exits with this phone !");
        });

        UserEntity createdUser = modelMapper.map(userDto, UserEntity.class);
        createdUser.setPublicId(idGenerator.generateStringId(30));
        createdUser.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        return modelMapper.map(userRepository.save(createdUser), UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        user.orElseThrow(() -> new UsernameNotFoundException("User not found with this email: " + email));

        return modelMapper.map(user.get(), UserDto.class);
    }

    @Override
    public List<OrderResponse> getOrdersByUser(String publicId) {
        UserEntity userEntity = userRepository.findByPublicId(publicId)
                .orElseThrow(() -> new EatItException("User not found with this id: " + publicId));
        List<OrderEntity> orders = orderRepository.findByUser(userEntity);

        List<OrderResponse> orderResponses = new ArrayList<>();
        orders.forEach(order -> {
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setPublicId(order.getPublicId());
            orderResponse.setAddress(order.getAddress());
            orderResponse.setDate(order.getDate());
            orderResponse.setStatus(order.getStatus());
            orderResponse.setUserPublicId(publicId);

            List<OrderProductEntity> orderProductEntities = orderProductRepository.findByOrder(order);
            orderProductEntities.forEach(orderProductEntity -> {
                orderResponse.getOrderProducts()
                        .add(new OrderProductResponse(orderProductEntity.getQuantity(),
                                orderProductEntity.getProduct().getPublicId(),
                                orderProductEntity.getProduct().getName()));
            });
            orderResponses.add(orderResponse);
        });
        return orderResponses;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        userEntity.orElseThrow(() -> new UsernameNotFoundException("User not found with this email: " + email));
        UserEntity user = userEntity.get();

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        Arrays.stream(user.getRoles().split(","))
                .forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));

        return new User(user.getEmail(), user.getEncryptedPassword(), authorities);
    }

}
