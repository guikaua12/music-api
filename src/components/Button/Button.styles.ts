'use client';
import styled, { Interpolation } from 'styled-components';
import { CSSProperties } from 'react';

export const ButtonContainer = styled.button<{ styles?: Interpolation<CSSProperties> }>`
    background-color: ${(props) => props.theme.primary};
    padding: 0.5rem 1rem;
    border-radius: 0.5rem;
    border: 0;
    cursor: pointer;

    ${(props) => props.styles}
`;
