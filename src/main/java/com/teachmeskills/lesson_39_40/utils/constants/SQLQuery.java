package com.teachmeskills.lesson_39_40.utils.constants;

public interface SQLQuery {
    String INSERT_INTO_IS_SAVE_USER = "INSERT INTO public.users (firstname, second_name, age, email, sex, telephone_number, created, updated, is_deleted) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    String INSERT_INTO_IS_SAVE_SECURITY = "INSERT INTO public.security (login, password, salt, role, created, updated, user_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    String INSERT_INTO_IS_SAVE_PRODUCT = "INSERT INTO public.product (name, price, created, updated) VALUES (?, ?, ?, ?)";
    String INSERT_INTO_IS_SAVE_USER_ACTION = "INSERT INTO public.user_actions (user_id, product_id, action, action_time) VALUES (?, ?, ?, ?)";
    String SELECT_FROM_LOGIN_VALIDATE = "SELECT * FROM security WHERE login = ?";
    String SELECT_GET_USER_ID = "SELECT * FROM users WHERE id = ? AND is_deleted = false";
    String SELECT_GET_ALL_USERS = "SELECT * FROM users WHERE is_deleted = false";
    String UPDATE_USER = "UPDATE users SET firstname = ?, second_name = ?, age = ?, email = ?, sex = ?, telephone_number = ?, updated = ? WHERE id = ? AND is_deleted = false";
    String DELETE_USER = "UPDATE users SET is_deleted = true WHERE id = ?";
    String SELECT_PRODUCT_BY_ID = "SELECT * FROM products WHERE id = ?";
    String UPDATE_PRODUCT = "UPDATE products SET name = ?, price = ?, updated = ? WHERE id = ?";
    String DELETE_PRODUCT = "DELETE FROM products WHERE id = ?";
    String SELECT_ALL_PRODUCTS = "SELECT * FROM products";

}
