delete from request_response_page_wise_time_details;
ALTER TABLE request_response_page_wise_time_details DROP CONSTRAINT IF EXISTS request_response_page_wise_time_details_pk;
ALTER TABLE request_response_page_wise_time_details ADD CONSTRAINT request_response_page_wise_time_details_pk PRIMARY KEY ("id");