import styled from "@emotion/styled";
import { Button, TextField } from "@mui/material";
import React from "react";
import { useForm } from "react-hook-form";
import { MdDoneAll } from "react-icons/md";
import { success } from "../consts";
import { useStore } from "../stores/storeContext";

type LoginInfo = {
  name?: string;
  pass?: string;
};

const Form = styled.form`
  display: flex;
  flex-direction: column;
  max-width: 300px;
  & > div {
    margin-bottom: 10px;
  }
  .red-error {
    background: #ffdede;
  }
`;

const SubmitButton = styled(Button)``;

const LoginForm = () => {
  const {
    register,
    handleSubmit,
    watch,
    formState: { errors },
  } = useForm();

  const { userStore } = useStore();

  const onSubmit = (data: LoginInfo) => {
    console.log(data);
    if (data.pass && data.name) {
      userStore.signup(data.name, data.pass);
    }
  };

  return (
    <Form onSubmit={handleSubmit(onSubmit)}>
      {watch("name") == "MAI" ? <MdDoneAll color={success} size={25} /> : null}
      <TextField
        label="login"
        variant="filled"
        className={errors.name ? "red-error" : ""}
        error={Boolean(errors.name)}
        {...register("name", {
          required: true,
          pattern: /^[a-z]+$/,
          validate: (value: string) => {
            if (value.length < 5) return "Минимальная длина == 4";
          },
        })}
        helperText={
          errors &&
          errors.name &&
          (errors.name.type == "required"
            ? "Это поле обязательное"
            : errors.name.message)
        }
      />
      <TextField
        error={errors.pass}
        label="password"
        className={errors.pass ? "red-error" : ""}
        variant="filled"
        {...register("pass", { required: true })}
        helperText={
          errors &&
          errors.pass &&
          (errors.pass.type == "required"
            ? "Это поле обязательное"
            : errors.pass.message)
        }
      />
      <SubmitButton type="submit" variant="contained">
        Login
      </SubmitButton>
    </Form>
  );
};

export default LoginForm;