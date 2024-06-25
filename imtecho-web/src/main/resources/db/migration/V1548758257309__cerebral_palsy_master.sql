DROP TABLE IF EXISTS public.rch_child_cerebral_palsy_master;
CREATE TABLE public.rch_child_cerebral_palsy_master(
id bigserial,
member_id bigint NOT NULL,
child_service_id bigint,
dob date,
hold_head_straight boolean,
hands_in_mouth boolean,
look_when_speak boolean,
make_noise_when_speak boolean,
look_in_direc_of_sound boolean,
sit_without_help boolean,
kneel_down boolean,
avoid_strangers boolean,
understand_no boolean,
enjoy_peekaboo boolean,
responds_on_name_calling boolean,
lifts_toys boolean,
mimic_others boolean,
drink_from_glass boolean,
run_independently boolean,
hold_things_with_finger boolean,
look_when_name_called boolean,
speak_simple_words boolean,
understand_instructions boolean,
tell_name_of_things boolean,
flip_pages boolean,
kick_ball boolean,
climb_updown_stairs boolean,
speak_two_sentences boolean,
like_playing_other_children boolean,
created_by bigint NOT NULL,
created_on timestamp without time zone NOT NULL,
modified_by bigint,
modified_on timestamp without time zone,
CONSTRAINT rch_child_cerebral_palsy_master_pkey PRIMARY KEY (id)
);