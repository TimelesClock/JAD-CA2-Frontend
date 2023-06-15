/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{jsp,html}"],
  theme: {
    extend: {},
  },
  plugins: [
    require('@tailwindcss/line-clamp'),
  ],
}

