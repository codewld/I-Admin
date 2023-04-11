/** @type {import('tailwindcss').Config} */
module.exports = {
  important: true,
  content: ['./index.html', './src/**/*.{vue,js,ts,jsx,tsx}'],
  theme: {
    extend: {
      backgroundImage: {
        'login': "url('/src/assets/img/login_bg.jpg')",
      },
      width: {
        '112': '28rem',
        '1/3D32': 'calc(33.3% - 32px)',
        '1/2D32': 'calc(50% - 32px)'
      },
      height: {
        '112': '28rem'
      }
    }
  },
  plugins: []
}
