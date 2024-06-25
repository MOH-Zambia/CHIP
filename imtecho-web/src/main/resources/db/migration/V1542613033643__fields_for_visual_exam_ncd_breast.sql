ALTER TABLE public.ncd_member_breast_detail
DROP COLUMN IF EXISTS agreed_for_self_breast_exam,
ADD COLUMN agreed_for_self_breast_exam boolean,
DROP COLUMN IF EXISTS visual_lump_in_breast,
ADD COLUMN visual_lump_in_breast text,
DROP COLUMN IF EXISTS visual_swelling_in_armpit,
ADD COLUMN visual_swelling_in_armpit text,
DROP COLUMN IF EXISTS visual_nipple_retraction_distortion,
ADD COLUMN visual_nipple_retraction_distortion text,
DROP COLUMN IF EXISTS visual_ulceration,
ADD COLUMN visual_ulceration text,
DROP COLUMN IF EXISTS visual_discharge_from_nipple,
ADD COLUMN visual_discharge_from_nipple boolean,
DROP COLUMN IF EXISTS visual_skin_dimpling_retraction,
ADD COLUMN visual_skin_dimpling_retraction text,
DROP COLUMN IF EXISTS visual_remarks,
ADD COLUMN visual_remarks text;