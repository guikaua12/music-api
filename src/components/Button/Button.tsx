import React, { ComponentProps, CSSProperties } from 'react';
import { ButtonContainer } from '@/components/Button/Button.styles';
import { Interpolation } from 'styled-components';

interface ButtonProps extends ComponentProps<'button'> {
    styles?: Interpolation<CSSProperties>;
}

const Button = ({ children, styles, ...props }: ButtonProps) => {
    return (
        <ButtonContainer styles={styles} {...props}>
            {children}
        </ButtonContainer>
    );
};

export default Button;
