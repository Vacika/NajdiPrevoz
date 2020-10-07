-- insert into public.users(birth_date, username, first_name, gender, last_name, password, phone_number, authority_id,
--                          profile_photo, default_lang, registered_on, is_activated, activation_token)
-- VALUES (now(), 'user1@mock.com', 'Mock', 'M', 'User1', '$2a$10$llYgqUzaiKrcXvbtl543D.IU/7nlcR9zcKLM9TQgj6zeXDN5AfqLa',
--         '071111111', 1, null, 'MK', now(), true, '$2a$10$llYgqUzaiKrcXvbtl543D.IU/7nlcR9zcKLM9TQgj6zeXDN5AfqLa'),
--        (now(), 'user2@mock.com', 'Mock', 'M', 'User2', '$2a$10$llYgqUzaiKrcXvbtl543D.IU/7nlcR9zcKLM9TQgj6zeXDN5AfqLa',
--         '072222222', 1, null, 'MK', now(), true, '$2a$10$llYgqUzaiKrcXvbtl543D.IU/7nlcR9zcKLM9TQgj6zeXDN5AfqLa'),
--        (now(), 'user3@mock.com', 'Mock', 'M', 'User3', '$2a$10$llYgqUzaiKrcXvbtl543D.IU/7nlcR9zcKLM9TQgj6zeXDN5AfqLa',
--         '073333333', 1, null, 'MK', now(), true, '$2a$10$llYgqUzaiKrcXvbtl543D.IU/7nlcR9zcKLM9TQgj6zeXDN5AfqLa'),
--        (now(), 'user4@mock.com', 'Mock', 'M', 'User4', '$2a$10$llYgqUzaiKrcXvbtl543D.IU/7nlcR9zcKLM9TQgj6zeXDN5AfqLa',
--         '074444444', 1, null, 'MK', now(), true, '$2a$10$llYgqUzaiKrcXvbtl543D.IU/7nlcR9zcKLM9TQgj6zeXDN5AfqLa'),
--        (now(), 'user5@mock.com', 'Mock', 'M', 'User5', '$2a$10$llYgqUzaiKrcXvbtl543D.IU/7nlcR9zcKLM9TQgj6zeXDN5AfqLa',
--         '075555555', 1, null, 'MK', now(), true, '$2a$10$llYgqUzaiKrcXvbtl543D.IU/7nlcR9zcKLM9TQgj6zeXDN5AfqLa');
--
--
-- -- PRESENT RIDES --
-- insert into public.rides(description, created_on, departure_time, price_per_head, total_seats_offered, to_location,
--                          driver_id, from_location, status, is_smoking_allowed, is_pet_allowed, has_air_condition,
--                          max_two_backseat)
-- VALUES ('', now(), now() + interval '1' hour, 100, 4, 1, 1, 2, 'ACTIVE', false, false, false, true),
--        ('', now(), now() + interval '2' hour, 120, 2, 2, 2, 3, 'ACTIVE', true, true, true, true),
--        ('', now(), now() + interval '2' hour, 140, 3, 3, 3, 4, 'ACTIVE', true, false, true, true),
--        ('', now(), now() + interval '3' hour, 160, 4, 4, 4, 6, 'ACTIVE', false, true, false, true),
--        ('', now(), now() + interval '1' hour, 180, 4, 5, 5, 8, 'ACTIVE', true, false, false, false);
--
-- -- Past Rides --
--
-- insert into public.rides(description, created_on, departure_time, price_per_head, total_seats_offered, to_location,
--                          driver_id, from_location, status, is_smoking_allowed, is_pet_allowed, has_air_condition,
--                          max_two_backseat)
-- VALUES ('', now() - interval '1' day, now() - interval '1' day, 100, 4, 2, 1, 8, 'FINISHED', false, false, false, true),
--        ('', now() - interval '1' day, now() - interval '1' day, 200, 4, 9, 2, 6, 'FINISHED', true, true, true, true),
--        ('', now() - interval '5' day, now() - interval '5' day, 250, 3, 4, 3, 2, 'FINISHED', true, false, true, true),
--        ('', now() - interval '8' day, now() - interval '8' day, 300, 4, 7, 4, 9, 'FINISHED', false, true, false, true),
--        ('', now() - interval '12' day, now() - interval '12' day, 350, 4, 3, 5, 1, 'FINISHED', true, false, false,
--         false);
--
--
-- insert into public.ride_requests(created_on, status, requester_id, ride_id, additional_description, requested_seats)
-- VALUES (now() - interval '1' day, 'APPROVED', 5, 1, '', 1),
--        (now() - interval '1' day, 'DENIED', 5, 1, '', 1),
--        (now() - interval '5' day, 'APPROVED', 5, 1, '', 1),
--        (now() - interval '8' day, 'APPROVED', 5, 1, '', 1),
--        (now() - interval '12' day, 'APPROVED', 5, 1, '', 1);
