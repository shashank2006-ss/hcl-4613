import React from 'react'
import { useFormStatus } from 'react-dom'

const SubmitButton = () => {
  const { pending } = useFormStatus()

  return (
    <button
      type="submit"
      disabled={pending}
      className={`flex w-full items-center justify-center gap-3 rounded-xl px-6 py-3.5
        text-base font-semibold text-white shadow-lg
        transition-all duration-300
        focus:outline-none focus:ring-4 focus:ring-indigo-300/40
        ${
          pending
            ? 'cursor-not-allowed bg-indigo-400/70'
            : 'bg-indigo-600 hover:-translate-y-0.5 hover:bg-indigo-700 hover:shadow-indigo-500/30 active:translate-y-0'
        }`}
    >
      {pending && (
        <svg
          className="h-5 w-5 animate-spin"
          xmlns="http://www.w3.org/2000/svg"
          fill="none"
          viewBox="0 0 24 24"
        >
          <circle
            className="opacity-25"
            cx="12"
            cy="12"
            r="10"
            stroke="currentColor"
            strokeWidth="4"
          />
          <path
            className="opacity-75"
            fill="currentColor"
            d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4z"
          />
        </svg>
      )}

      {pending ? 'Submitting...' : 'Submit'}
    </button>
  )
}

export default SubmitButton