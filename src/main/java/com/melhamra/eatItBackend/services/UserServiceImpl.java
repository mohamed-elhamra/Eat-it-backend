package com.melhamra.eatItBackend.services;

import com.melhamra.eatItBackend.dtos.UserDto;
import com.melhamra.eatItBackend.entities.UserEntity;
import com.melhamra.eatItBackend.repositories.UserRepository;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

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
        userByEmail.ifPresent(user -> {throw new RuntimeException("User already exits with this email !");});

        Optional<UserEntity> userByPhone = userRepository.findByPhone(userDto.getPhone());
        userByPhone.ifPresent(user -> {throw new RuntimeException("User already exits with this phone !");});

        UserEntity createdUser = modelMapper.map(userDto, UserEntity.class);
        createdUser.setUserId(idGenerator.generateStringId(30));
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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        userEntity.orElseThrow(() -> new UsernameNotFoundException("User not found with this email: " + email));
        UserEntity user = userEntity.get();

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        Arrays.stream(user.getRoles().split(","))
                .forEach(role-> authorities.add(new SimpleGrantedAuthority(role)));

        return new User(user.getEmail(), user.getEncryptedPassword(), authorities);
    }

}
