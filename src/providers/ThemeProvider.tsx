'use client';
import React, { ReactNode } from 'react';
import { ThemeProvider as StyledThemeProvider } from 'styled-components';
import { DefaultTheme } from 'styled-components';

export const theme: DefaultTheme = {
    primary: '#FF8202',
    secondary: '#03C4FF',
    neutral1: '#262626',
    neutral2: '#141414',
    neutral3: '#121212',
    neutral4: '#333333',
    neutral5: '#808080',
};

const ThemeProvider = ({ children }: { children: ReactNode }) => {
    return <StyledThemeProvider theme={theme}>{children}</StyledThemeProvider>;
};

export default ThemeProvider;
