import { Tab } from '@headlessui/react'
import React, { Fragment, ReactNode } from 'react'

interface TabsProps {
    titles: string[]
    elements: ReactNode[]
}

const Tabs = ({
    titles,
    elements
}: TabsProps) => {
    return (
        <Tab.Group>
            <Tab.List className="flex border-b-slate-200 border-b">
                {
                    titles.map(title => (
                        <Tab key={title} as={Fragment}>
                            {({ selected }) => (
                                <div
                                    className={`px-4 py-2 cursor-pointer w-max focus:outline-none font-semibold
                                    ${selected ? 'border-b-primary-500 border-b-2 text-primary-500' : 'bg-white text-slate-600'}
                            `}
                                >
                                    {title}
                                </div>
                            )}
                        </Tab>
                    ))
                }
            </Tab.List>
            <Tab.Panels>
                {
                    elements.map((element, i) => (
                        <Tab.Panel className="my-4" key={i}>
                            {element}
                        </Tab.Panel>
                    ))
                }
            </Tab.Panels>
        </Tab.Group>
    )
}

export default Tabs