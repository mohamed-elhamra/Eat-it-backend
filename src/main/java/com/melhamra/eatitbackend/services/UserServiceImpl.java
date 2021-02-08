package com.melhamra.eatitbackend.services;

import com.melhamra.eatitbackend.dtos.UserDto;
import com.melhamra.eatitbackend.entities.OrderEntity;
import com.melhamra.eatitbackend.entities.OrderProductEntity;
import com.melhamra.eatitbackend.entities.UserEntity;
import com.melhamra.eatitbackend.exceptions.EatItException;
import com.melhamra.eatitbackend.repositories.OrderProductRepository;
import com.melhamra.eatitbackend.repositories.OrderRepository;
import com.melhamra.eatitbackend.repositories.UserRepository;
import com.melhamra.eatitbackend.responses.OrderProductResponse;
import com.melhamra.eatitbackend.responses.OrderResponse;
import com.melhamra.eatitbackend.utils.IDGenerator;
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
import java.util.stream.Collectors;

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
            throw new EatItException("User already exits with this email");
        });

        Optional<UserEntity> userByPhone = userRepository.findByPhone(userDto.getPhone());
        userByPhone.ifPresent(user -> {
            throw new EatItException("User already exits with this phone");
        });

        UserEntity createdUser = modelMapper.map(userDto, UserEntity.class);
        createdUser.setPublicId(idGenerator.generateStringId(15));
        createdUser.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        return modelMapper.map(userRepository.save(createdUser), UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with this email: " + email));

        return modelMapper.map(user, UserDto.class);
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
            orderProductEntities.forEach(orderProductEntity -> orderResponse.getOrderProducts()
                    .add(new OrderProductResponse(orderProductEntity.getQuantity(),
                            orderProductEntity.getPrice(),
                            orderProductEntity.getProduct().getPublicId(),
                            orderProductEntity.getProduct().getName())));
            orderResponses.add(orderResponse);
        });
        return orderResponses;
    }

    @Override
    public UserDto getUserByPublicId(String publicId) {
        UserEntity userEntity = userRepository.findByPublicId(publicId)
                .orElseThrow(() -> new EatItException("User not found with this id: " + publicId));
        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with this email: " + email));

        Collection<GrantedAuthority> authorities =
                Arrays.stream(user.getRoles().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        return new User(user.getEmail(), user.getEncryptedPassword(), authorities);
    }

}
