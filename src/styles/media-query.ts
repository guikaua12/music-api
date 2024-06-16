import { css } from 'styled-components';

const device = {
    xs: '400px',
    sm: '600px',
    md: '900px',
    lg: '1280px',
    xl: '1440px',
    xxl: '1920px',
};

export const media = {
    xs: (...args) => css`
        @media (max-width: ${device.xs}) {
            ${css(...args)};
        }
    `,
    sm: (...args) => css`
        @media (max-width: ${device.sm}) {
            ${css(...args)};
        }
    `,
    md: (...args) => css`
        @media (max-width: ${device.md}) {
            ${css(...args)};
        }
    `,
    lg: (...args) => css`
        @media (max-width: ${device.lg}) {
            ${css(...args)};
        }
    `,
    xl: (...args) => css`
        @media (max-width: ${device.xl}) {
            ${css(...args)};
        }
    `,
    xxl: (...args) => css`
        @media (max-width: ${device.xxl}) {
            ${css(...args)};
        }
    `,
};
